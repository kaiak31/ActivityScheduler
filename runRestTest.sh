cd $(dirname $0)
mvn clean package
java -jar target/service-scheduler-0.1.0.jar &
PID=$!
sleep 15
curl --data "merchantId=90210&description=Inshan" http://localhost:8080/scheduler/addActivity > target/addActivity.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/addSlot > target/addSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/addSlot > target/shouldBeErrorSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12&ownerId=666" http://localhost:8080/scheduler/addSlot > target/validEvilSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/checkAvailability > target/validCheckAvailablity.json

curl --data "merchantId=90210&description=Inshan" http://localhost:8080/scheduler/addActivity > target/addActivity.json

kill -9 $PID

echo "Please check the json output file. They should have no errors"
exit
