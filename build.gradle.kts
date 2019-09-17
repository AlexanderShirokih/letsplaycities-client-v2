plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.41"
}

group = "ru.quandastudio.lps"
version = "0.2.0"

val sourcesJar by tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts.add("archives", sourcesJar)

publishing {
    publications {
        create<MavenPublication>("lpsClientLibrary") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
    repositories {
        mavenLocal()
    }
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
    compile(kotlin("reflect"))
}