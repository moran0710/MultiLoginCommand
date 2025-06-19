plugins {
    id("java")
}



group = "top.molab.minecraft"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven {
        name="sonatype"
        url=uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}