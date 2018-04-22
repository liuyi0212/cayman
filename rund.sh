gradle clean build -x test; nohup java -Dspring.profiles.active=$1 -jar build/libs/cayman-1.0.jar &
