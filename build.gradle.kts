plugins {
    id("java")
}

group = "dev.xoapp.worldguard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.maven.apache.org/maven2/")
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation(files("library/FormAPI-2.2-SNAPSHOT.jar"))
    compileOnly("com.github.PowerNukkitX:PowerNukkitX:master-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}