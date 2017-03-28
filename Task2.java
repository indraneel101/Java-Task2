/* Dockerfile for Simple Gradle Plugin-made by Indraneel Sanyal

task 2: Gradle Plugin for Docker Image File
*/

//Here is my simple Docker file for Java Projects

    FROM openjdk:8

    RUN wget -q https://services.gradle.org/distributions/gradle-3.3-bin.zip \
        && unzip gradle-3.3-bin.zip -d /opt \
        && rm gradle-3.3-bin.zip

    ENV GRADLE_HOME /opt/gradle-3.3
    ENV PATH $PATH:/opt/gradle-3.3/bin


//The imamge can be easily built using the command

    docker build -t gradle-3.3
	 
//Run Docker//
      
	docker run --rm -v "$PWD":/usr/src/project -w /usr/src/project gradle buildscript {

    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.bmuschko:gradle-docker-plugin:3.0.3'
    }
}
apply plugin: 'com.bmuschko.docker-remote-api'
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
docker {
    if (System.env.containsKey('DOCKER_HOST') && System.env.containsKey('DOCKER_CERT_PATH')) {
        url = System.env.DOCKER_HOST.replace("tcp", "https")
        certPath = new File(System.env.DOCKER_CERT_PATH)
    }
}
task buildImage(type: DockerBuildImage) {
    dependsOn assemble
    inputDir = project.rootDir
    tag = "arquillian/game-service:${project.version}"
}


