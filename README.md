# wow-market

Spring Boot 기반 쇼핑몰 포트폴리오 프로젝트입니다. 회원가입, 로그인, 상품 탐색, 장바구니, 주문까지 브라우저에서 바로 확인할 수 있습니다.

## Vercel 배포 (UI 데모)

Spring Boot 서버는 Vercel에서 실행되지 않습니다. `public/` 폴더의 정적 데모를 Vercel에 배포하면 쇼핑몰 UI를 바로 볼 수 있습니다.

Vercel 프로젝트 설정:

- Framework Preset: **Other**
- Build Command: 비워두기
- Output Directory: **public**

GitHub 연동 후 자동 재배포됩니다.

## 로컬 실행 (Spring Boot 전체 기능)

```powershell
.\gradlew.bat bootRun
```

접속:

```text
http://localhost:8181
```

## 핵심 기능

- Spring Security 기반 회원가입/로그인/로그아웃
- JPA 엔티티 기반 회원, 상품, 장바구니, 주문 모델
- 카테고리별 상품 필터링
- 장바구니 수량 증가 및 삭제
- 주문 생성과 주문 내역 조회
- H2 인메모리 DB와 샘플 상품 자동 적재

## 면접 어필 포인트

- 단순 화면이 아니라 인증, 도메인, 영속성, 주문 흐름이 연결되어 있습니다.
- Controller, Service, Repository, Entity를 분리했습니다.
- 비밀번호는 BCrypt로 해싱합니다.
- UI는 Thymeleaf 서버 렌더링으로 빠르게 시연 가능합니다.
