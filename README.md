# 배달 서비스 프로젝트

![delivery_img.jpg](img%2Fdelivery_img.jpg)

### 👊 Spring Data JPA 팀 단위 개발 숙달을 위한 배달 서비스 백엔드 애플리케이션 서버 프로젝트 👊

#### 프로젝트 진행 기간: 24.11.01 ~ 24.11.07

## 프로젝트 목표

#### Spring AOP 관점에서 Spring 의 다양한 기술들을 활용하여 배달 서비스 구현 <br>

## 👨‍👨‍👧‍👧 팀 구성

| 이름    | 역할 | 담당 기능                                        |
|-------|----|----------------------------------------------|
| 김동주   | 팀원 | 회원가입, 로그인, 회원정보수정, 회원탈퇴, Spring Security JWT |
| 이은영   | 팀원 | 사업자 주문 관리(조회, 상태 변경), 메뉴 관리(등록, 수정, 삭제)      |
| 백현욱   | 팀원 | 사업자 가게 관리(등록, 수정, 삭제, 조회)                    |
| 장재혁   | 팀원 | 고객 Home 화면(가게 검색), 주문하기, 주문 조회               |
| 박가온누리 | 팀장 | 고객 리뷰 작성, 조회, 삭제, GlobalException            |

## Tools

### 🖥 language & Server 🖥

<img src="https://img.shields.io/badge/intellij idea-207BEA?style=for-the-badge&logo=intellij%20idea&logoColor=white"> <br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <hr>

### 👏 Cowork Tools 👏

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <br> 
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br>
<hr/>

## 와이어 프레임

## 회원 관리 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>회원가입</td>
        <td>POST</td>
        <td><code>/api/members/signup</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>USER, OWNER</td>
    </tr>
     <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>/api/members/login</code></td>
        <td><pre lang="json">{
    "email": String,
    "password": String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "email": String,
    "password": String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER, OWNER</td>
    </tr>
    <tr>
        <td>회원 정보 조회</td>
        <td>GET</td>
        <td><code>/api/members/profile</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER, OWNER</td>
    </tr>
    <tr>
        <td>회원 정보 수정</td>
        <td>PUT</td>
        <td><code>/api/members/profile</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER, OWNER</td>
    </tr>
    <tr>
        <td>비밀번호 변경</td>
        <td>PUT</td>
        <td><code>/api/members/password</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>204</td>
        <td>USER, OWNER</td>
    </tr>
    <tr>
        <td>회원 탈퇴</td>
        <td>PATCH</td>
        <td><code>/api/members</code></td>
        <td><pre lang="json">{
    "password": String
}</pre></td>
        <td><code>application/json</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>204</td>
        <td>USER, OWNER</td>
</table>

## 사업자 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>가게 등록</td>
        <td>POST</td>
        <td><code>/api/seller/stores</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>OWNER</td>
    </tr>
     <tr>
        <td>가게 전체 조회</td>
        <td>GET</td>
        <td><code>/api/seller/stores</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td><pre lang="json">{
    "email": String,
    "password": String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>특정 가게 조회</td>
        <td>GET</td>
        <td><code>/api/seller/stores/{storeId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>가게 정보 수정</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>PathVariable, application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>가게 폐업</td>
        <td>PATCH</td>
        <td><code>/api/seller/stores/{storeId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>가게별 주문 조회</td>
        <td>GET</td>
        <td><code>/api/seller/stores/{storeId}/deliveries</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable, RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>주문 상태 변경</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}/deliveries/{deliveryId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable, RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 등록</td>
        <td>POST</td>
        <td><code>/api/seller/stores/{storeId}/menus</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 수정</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}/menus/{menuId}</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>PathVariable, application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 삭제</td>
        <td>PATCH</td>
        <td><code>/api/seller/stores/{storeId}/menus/{menuId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
</table>

## 이용자 API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>가게 전체 조회</td>
        <td>GET</td>
        <td><code>/api/stores</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>카테고리별 가게 조회</td>
        <td>GET</td>
        <td><code>/api/stores</code></td>
        <td><code>N/A</code></td>
        <td><code>RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>검색별 가게 조회</td>
        <td>GET</td>
        <td><code>/api/stores</code></td>
        <td><code>N/A</code></td>
        <td><code>RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>특정 가게 조회</td>
        <td>GET</td>
        <td><code>/api/stores/{storeId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>주문 하기</td>
        <td>GET</td>
        <td><code>/api/stores/{storeId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>가게 정보 수정</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>PathVariable, application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>가게 폐업</td>
        <td>PATCH</td>
        <td><code>/api/seller/stores/{storeId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>가게별 주문 조회</td>
        <td>GET</td>
        <td><code>/api/seller/stores/{storeId}/deliveries</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable, RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>주문 상태 변경</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}/deliveries/{deliveryId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable, RequestParam</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 등록</td>
        <td>POST</td>
        <td><code>/api/seller/stores/{storeId}/menus</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 수정</td>
        <td>PUT</td>
        <td><code>/api/seller/stores/{storeId}/menus/{menuId}</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>PathVariable, application/json</code></td>
        <td><pre lang="json">{
    email: String,
    username: String,
    nickname: String,
    password: String,
    passwordCheck: String,
    role: String,
    phone: String,
    address: String
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
    <tr>
        <td>메뉴 삭제</td>
        <td>PATCH</td>
        <td><code>/api/seller/stores/{storeId}/menus/{menuId}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>OWNER</td>
    </tr>
</table>


## ERD

```mermaid
---
title: Delivery Service Diagram
---
erDiagram
    MEMBER {
bigint id pk
        varchar email
varchar username
varchar nickname
varchar password
varchar role
tinyint is_deleted
varchar address
varchar phone
datetime created_At
datetime updated_At

}

STORE {
bigint id pk
varchar name
datetime open_time
datetime close_time
int min_price
varchar category
varchar address
varchar license
tinyint togo
varchar status
datetime created_At
datetime updated_At
datetime deleted_At
bigint member_id
}

MENU {
bigint id pk
varchar name
int price
varchar description
tinyint is_deleted
datetime created_At
datetime updated_At
bigint store_id
}

DELIVERY {
bigint id pk
varchar datails
varchar status
int total_price
bigint member_id
bigint store_id
}

DELIVERY_MENU {
bigint id pk
int quantity
int price
bigint order_id
bigint menu_id
}

REVIEW {
bigint id pk
int score
varchar comment
datetime created_At
datetime updated_At
bigint store_id
bigint member_id
}
```
