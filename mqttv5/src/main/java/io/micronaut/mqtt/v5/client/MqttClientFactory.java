/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.mqtt.v5.client;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.mqtt.v5.config.MqttClientConfigurationProperties;
import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttClientPersistence;
import org.eclipse.paho.mqttv5.client.TimerPingSender;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A factory to create an MQTT client.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Factory
public final class MqttClientFactory {

    @Singleton
    @Bean(preDestroy = "disconnect")
    MqttAsyncClient mqttClient(MqttClientConfigurationProperties configuration,
                               @Nullable MqttClientPersistence clientPersistence,
                               @Named(TaskExecutors.MESSAGE_CONSUMER) ExecutorService executorService) throws MqttException {
        ScheduledExecutorService consumerExecutor = (ScheduledExecutorService) executorService;
        MqttAsyncClient client = new MqttAsyncClient(configuration.getServerUri(), configuration.getClientId(), clientPersistence, new TimerPingSender(consumerExecutor), consumerExecutor);
        client.connect(configuration.getConnectOptions())
                .waitForCompletion(configuration.getConnectionTimeout().toMillis());
        return client;
    }
}
