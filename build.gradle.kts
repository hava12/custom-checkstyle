plugins {
    id("java")
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

allprojects {
    group = "com.custom-checkstyle"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("com.puppycrawl.tools:checkstyle:10.21.1")
}

group = "com.hava12.custom-checkstyle"
version = "1.0-SNAPSHOT"


// Fat JAR 생성 설정
tasks.register<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("fatJar") {
    archiveBaseName.set("custom-checkstyle-fat")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("") // classifier 제거 (기본 JAR로 만듦)

    mergeServiceFiles() // 서비스 파일 병합
    from(sourceSets.main.get().output)
    manifest {
        attributes["Main-Class"] = "com.puppycrawl.tools.checkstyle.Main"
    }

    configurations = listOf(project.configurations.runtimeClasspath.get())
    dependencies {
        include(dependency("com.puppycrawl.tools:checkstyle:10.21.1"))
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.puppycrawl.tools.checkstyle.Main"
    }

    // META-INF 폴더의 서명 파일 제거
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hava12/custom-checkstyle")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}