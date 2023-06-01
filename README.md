# Event Emitter Loader

>**GOV.UK Verify has closed**
>
>This repository is out of date and has been archived

## Build

```bash
./gradlew build shadowJar
```

## Configure

Create configuration file with structure:
```yaml
eventEmitterConfiguration:
  enabled: true
  accessKeyId: <access key for calling API Gateway>
  secretAccessKey: <access key secret for calling API Gateway>
  region: eu-west-2
  encryptionKey: <encrypted key used to encrypt events (encypted using KMS)>
 
```
A template file is provided in `configuration/eventloader.yml.template`.

The template file allows you to specify the configuration using environment variables. You will need
to set the following variables:

```bash
export EVENT_EMITTER_ENCRYPTION_KEY=<encrypted encryption key>
export EVENT_EMITTER_API_GATEWAY_URL=<URL to the SQS queue>
export API_GATEWAY_AWS_ACCESS_KEY_ID=<access key>
export API_GATEWAY_AWS_SECRET_ACCESS_KEY=<secret>
export AWS_REGION=eu-west-2
```

## Running

Before running any of the below commands, you need to use the AWS CLI tools to authenticate.

You can use the `tools/assume-role.py` tool in the `alphagov/verify-event-infrastructure` repository, e.g.:

```bash
 eval $(../verify-event-infrastructure/tools/assume-role.py -t audit-billing-admin-dev -m <AWS OTP code>)
```

### Create single event

To create a single event in the queue:

```bash
java -jar build/libs/event-creator-1.0-SNAPSHOT-all.jar create -t <event_type> -d '<session details as JSON >' -s "<session_id>" -o "<originating_service>" [ --timestamp "<timestamp as YYYY-MM-ddTHH:mm:ss.iiiZ>" ] -c <path to config file>
```


For example:
```bash
java -jar build/libs/event-creator-1.0-SNAPSHOT-all.jar create -t session_event -d '{"session_event_type": "idp_authn_success"}' -s "b6289a9f-1b01-49ec-b3be-cd0aa501e280" -o "policy" --timestamp "2019-01-01T06:30:00.000Z" -c configuration/eventloader.yml
```

The `eventId` is automatically generated and if not specified on the command line, the `timestamp` field
will include the current date and time. All other fields must be included on the command line.

### Load events from file

To load a set of pre-configured events you first need to produce a file containing an array of
JSON representations of the events, for example:

```json
[
  {
    "eventId": "b27fb3f0-c955-429d-9a53-e3ece111fd20",
    "timestamp": 1546324200000,
    "event_type": "session_event",
    "originating_service": "policy",
    "details": {
        "message_id": "_12345678901234567890ABCDEF123456",
        "request_id": "_12345678901234567890ABCDEF123456",
        "session_event_type": "session_started",
        "session_expiry_time": "2019-01-01T12:00:00.000Z",
        "transaction_entity_id": "https://idp-entity-id",
        "minimum_level_of_assurance": "LEVEL_2",
        "required_level_of_assurance": "LEVEL_2",
        "principal_ip_address_as_seen_by_hub": "111.222.222.111 10.0.0.1"
    }
  },
  {
    "eventId": "9b940355-36db-44b4-bee8-b13de18ae150",
     "timestamp": 1546324200000,
     "event_type": "session_event",
     "originating_service": "policy",
     "details": {
         "message_id": "_12345678901234567890ABCDEF123456",
         "request_id": "_12345678901234567890ABCDEF123456",
         "session_event_type": "idp_selected",
         "session_expiry_time": "2019-01-01T12:00:00.000Z",
         "transaction_entity_id": "https://idp-entity-id",
         "minimum_level_of_assurance": "LEVEL_2",
         "required_level_of_assurance": "LEVEL_2",
         "principal_ip_address_as_seen_by_hub": "111.222.222.111 10.0.0.1"
     }
   }
]
```
