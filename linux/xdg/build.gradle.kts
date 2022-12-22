plugins {
    java
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(projects.autoDarkModeBase)
    implementation(libs.linux.dbus.core) {
        exclude(group = "org.slf4j")
    }
    implementation(libs.linux.dbus.transport) {
        exclude(group = "org.slf4j")
    }
    implementation(libs.kotlinx.coroutines.core.jvm)

    ksp(libs.autoservice.processor)
    compileOnly(libs.autoservice.annotations)
    compileOnly(kotlin("stdlib-jdk8"))
}
