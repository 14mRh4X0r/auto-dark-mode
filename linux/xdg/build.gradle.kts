plugins {
    java
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(projects.autoDarkModeBase)
    implementation(libs.linux.dbus.core)
    implementation(libs.linux.dbus.transport)
    implementation(libs.kotlinx.coroutines.core.jvm)

    ksp(libs.autoservice.processor)
    compileOnly(libs.autoservice.annotations)
    compileOnly(kotlin("stdlib-jdk8"))
}
