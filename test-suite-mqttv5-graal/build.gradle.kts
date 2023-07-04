import io.micronaut.testresources.buildtools.KnownModules.HIVEMQ

plugins {
    id("io.micronaut.build.internal.graal-test")
}

dependencies {
    testImplementation(projects.micronautMqttv5)
}

micronaut {
    testResources {
        additionalModules.add(HIVEMQ)
    }
}
