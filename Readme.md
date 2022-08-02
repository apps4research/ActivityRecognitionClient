# Activity Recognition Test App

The Activity Recognition API can detect the following activities:

**STILL:** When the mobile device will be still i.e. the user is either sitting at someplace or the mobile device is having no motion, then the Activity Recognition Client will detect the STILL activity.

**ON_FOOT:** When the mobile device is moving at a normal speed i.e. the user carrying the mobile device is either walking or running then the Activity Recognition Client will detect the ON_FOOT activity.

**WALKING:** This is a sub-activity of the ON_FOOT activity which is detected by the Activity Recognition Client when the user carrying the mobile device is walking.

**RUNNING:** This is also a sub-activity of ON_FOOT activity which is detected by the Activity Recognition Client when the user carrying the mobile device is running.

**IN_VEHICLE:** This activity detected when the mobile device is on the bus or car or some other kind of vehicle or the user holding the mobile device is present in the vehicle.

**ON_BICYCLE:** When the device is on the bicycle or the user carrying the mobile is on a bicycle then this activity will be detected.

**TILTING:** When the mobile device is being lifted and is having some angle with the flat surface then the Activity Recognition Client will detect this activity.

**UNKNOWN:** The Activity Recognition Client will show this result when the device is unable to detect any activity on the mobile device.

### Important:
To optimize resources (Battery life), the API may stop activity reporting if the device has been still for a while, and uses low power sensors to resume reporting when it detects movement. See also https://developers.google.com/location-context/activity-recognition

Requires additional permissions beyond API 28