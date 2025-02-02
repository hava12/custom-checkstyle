plugins {
    id("java")
    `maven-publish`
}
// 참고 : https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry

group = "com.hava12.custom-checkstyle"
version = "1.0-SNAPSHOT"

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