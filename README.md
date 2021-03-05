# Spring REST Token

## Summary

Sample spring security rest api project using token authentication

## Sample API

### Login

```
## Admin Url
curl "http://localhost:8080/admin" \
     -H 'X-Auth-Token: 2d36ec4b-1b8a-4ce0-bc72-e7c1c38d4295'
```

### Secured API

```
curl "http://localhost:8080/admin" \
     -H 'X-Auth-Token: 2d36ec4b-1b8a-4ce0-bc72-e7c1c38d4295'
```

[More Samples](demo.paw)