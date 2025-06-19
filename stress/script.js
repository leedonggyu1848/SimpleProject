import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 100, // 가상 사용자 수 (Virtual Users)
  duration: '120s', // 테스트 지속 시간
};

export default function () {
  const res = http.get('http://localhost:8080/trash/add');
  check(res, {
    'status was 200': (r) => r.status === 200,
  });
}
