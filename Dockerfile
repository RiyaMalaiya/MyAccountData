# docker build command - $docker build -f Dockerfile -f {name of image whatever you want} .

#run container - $docker run container - docker run -p 8000:7080 {image name} .

# to see all images- $docker image ls

# to see all container- $docker container ls

#docker remove image - $docker rm {image name} -f

# for scalin initiate docker swarm - $docker swarm init

#create replica container = $docker service create --name web -p 8000:7080 --replica 10 {image name}

# to see all replica- $docker service ls

# remove all replica you have to provide replica name - $docker service rm web -f


FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ./target/MyAccountData-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]