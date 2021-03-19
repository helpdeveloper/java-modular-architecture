import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomSeed } from 'k6';

export let options = {
  stages: [
    { duration: '5s', target: 1 },
    { duration: '8s', target: 2 },
    { duration: '12s', target: 5 },
    { duration: '2s', target: 1 },
  ],
  thresholds: {
      http_req_duration: ['p(90) < 200', 'p(95) < 500', 'p(99.9) < 1200'],
    }
};
export default function () {
  randomSeed(123456789);
  let rnd = Math.random();
  let data = {
               "scheduleDate": "2022-01-01T00:00:00.000Z",
               "body": "string" + rnd,
               "recipient": {
                 "name": "string" + rnd,
                 "email": "string" + rnd,
                 "phoneNumber": "string" + rnd,
                 "phoneId": "string" + rnd
               },
               "channel": "EMAIL"
             };
  let headers = { 'Content-Type': 'application/json' };
  let res = http.post('http://localhost:8080/v1/message/', JSON.stringify(data), { headers: headers });
  let id = JSON.parse(res.body).id;
  check(res, { 'status was 201': (r) => r.status == 201 });
  let detail = http.get('http://localhost:8080/v1/message/' + id);
  check(detail, { 'status was 200': (r) => r.status == 200 });
  var random_boolean = (rnd % 2 == 1);
  let deleteData = http.del('http://localhost:8080/v1/message/' + id);
  check(deleteData, { 'status was 204': (r) => r.status == 204 });
  sleep(1);
}