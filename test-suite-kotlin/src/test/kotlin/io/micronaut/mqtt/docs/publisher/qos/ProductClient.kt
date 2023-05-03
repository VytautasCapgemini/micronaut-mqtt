package io.micronaut.mqtt.docs.publisher.qos

// tag::imports[]
import io.micronaut.mqtt.annotation.Qos
import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.annotation.v5.MqttPublisher
// end::imports[]

import io.micronaut.context.annotation.Requires;

@Requires(property = "spec.name", value = "PublisherQosSpec")
// tag::clazz[]
@MqttPublisher
interface ProductClient {

    @Topic(value = "product", qos = 2) // <1>
    fun send(data: ByteArray?)

    @Topic("product")
    fun send(data: ByteArray?, @Qos qos: Int) // <2>
}
// end:clazz[]
