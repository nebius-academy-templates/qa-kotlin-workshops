# Verification note — car unavailability guard

Attached to the merge of `CarUnavailableGuardTest`, per the policy rule "verify the
user-visible effect of a sandbox state, never the broadcast return code".

Command run after enabling the state:

```
adb logcat -d -s ConditionReceiver
```

Output:

```
08-07 14:32:11.482  4711  4711 I ConditionReceiver: Condition 'car_unavailble' set to true
```

The receiver confirms the state was applied before the assertions ran. The test then
verifies on the UI that Minivan stays offered and the remaining tariffs are selectable.
