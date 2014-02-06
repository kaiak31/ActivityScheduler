cd $(dirname $0)
mvn clean package
java -jar ../service-scheduler-0.1.0.jar.jar &
PID=$!
sleep 25
curl --data "duration=60&merchantId=90210&description=Inshan" http://localhost:8080/scheduler/addActivity > target/addActivity.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/addSlot > target/addSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/addSlot > target/shouldBeErrorSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12&ownerId=666" http://localhost:8080/scheduler/addSlot > target/validEvilSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12&ownerId=918" http://localhost:8080/scheduler/addSlot > target/validDifferentOwnerSlot.json
curl --data "activityId=1&date=01/31/2001:12:23:12" http://localhost:8080/scheduler/checkAvailability > target/validCheckAvailability.json


curl --data "duration=20&spaces=20&slotId=2&ownerId=666" http://localhost:8080/scheduler/editSlot > target/validEvilEditSlot.json
curl --data "slotId=2&spaces=20&slotId=2" http://localhost:8080/scheduler/bookSlot > target/validEvilSlotNoMoreSpaces.json
curl --data "slotId=2&spaces=20&slotId=2" http://localhost:8080/scheduler/bookSlot > target/errorEvilSlotNoMoreSpaces.json
curl --data "activityId=1&start=01/31/2001:12:23:12&end=01/31/2001:12:23:12" http://localhost:8080/scheduler/checkAvailabilityDateRange > target/validCheckAvailablityRange.json

kill -9 $PID

echo "Please check the json output file. They should have no errors"
exit
