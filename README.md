# TODO LIST
- README.md 파일에 문제해결 전략 및 프로젝트 빌드, 실행 방법 명시


### 빌드 & 실행
* gradle 빌드 자동화 시스템으로 과제 수행  
    * $ brew update
    * $ brew install gradle
    * gradle build
    * gradle bootRun

### 문제해결 전략
* 문제1 : 사용자는 텍스트로 된 할일을 추가할 수 있다.
    * 기본적으로 단건만 추가가능하다. 
    * 다건(Json List)로 받는 구조를 추가한다면 URI를 추가적으로 설계 (ex : todos/many)
    * sample json : {"contents":"sample" , "statusType":"N"}
    * statusType을 지정하지 않으면 자동으로 N, YN으로 컬럼을 설정하려했으나 더 많은 행위가 추가될수 있을거 같아 타입으로 컬럼 생성
    * http://localhost:8080/ 진입하여 sample json 작성 후 POST 버튼 실행
    
* 문제2 : 할일 추가 시 다른 할일들을 참조 걸 수 있다.
    * 참조할 할일(id)이 없으면 참조를 불가능하다.
    * 추가하는 할일의 완료상태가 N이면 참조되어있는 할일들의 상태가 전체 N이 되어야 한다. Y이면 불가능하다.
    * 참조되는 할일이 자신이면 참조할 수 없다.
    * 1 -> 3 , 3 <- 1 역참조도 불가능하다. (수정시)
    * 위의 경우가 아니면 정상참조로 보고 추가한다.
    
* 문제3 : 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고)
    * 예시대로 @1 @2의 경우 참조한다. {"contents":"a @1"}
    * 숫자로 캐스팅되는 형태가 아니면 참조 되지 않는다.
    
* 문제4 : 사용자는 할일을 수정할 수 있다.
    * 문제3과 동일한 조건이 베이스조건.
    * 추가적으로 할일 수정시 완료처리가 Y라면 참조되는 할일들이 Y여야 처리된다.
    * 위의 조건이 통과되면 수정 가능하다.

* 문제5 : 사용자는 할일 목록을 조회할 수 있다.
    * 작성일, 최종수정일, 내용(뒷 Like), 상태, id or ids 형태로 조회 가능
    * /todos/ 전체 조회 , /todos/1 id단건 조회 /todos?ids=1,2,3 다건조회
    * 전체조회 시 페이징요소인 totalCount, offset, limit 값을 추가하였다.
    * totalCount는 매번 쿼리시 Count를 돌리는건 불필요하다 보고 집계 테이블(TODO_ACCUM)을 만들어서 관리함.

* 문제 6: : 사용자는 할일을 완료처리 할 수 있다.
    * 문제3, 문제4의 조건이 필수조건
    * 조건에 부합하면 추가, 수정시 완료처리가 가능하다.  

### 과제 요구사항
* 웹 어플리케이션으로 개발
    * Springboot + mybatis + h2 + Thymeleaf 
* 웹어플리케이션 개발언어는 Java, Scala, Golang 중 선택을 권장함 (단, 다른 언어에 특별히 자신있는 경우 선택에 제한을 두지 않음)
    * JAVA로 구현
* 서버는 REST API로 구현
    * REST API 스팩으로 구현
* 프론트엔드 구현방법은 제약 없음
    * Thymeleaf + jquery + bootstrap ( http://localhost:8080/ )
    * ajax call -> return json 을 보여주는 형태로 구현 
* 데이타베이스는 사용에 제약 없음 (가능하면 In-memory db 사용)
    * h2
* 단위테스트 필수, 통합테스트는 선택
    * TestCase 작성 ( kapay\src\test\java\com\kakaopay\todo\ApplicationTest.java )
    * 통합테스트는 구현하지 못함. 단위테스트 단위만 구현
* README.md 파일에 문제해결 전략 및 프로젝트 빌드, 실행 방법 명시
    * 작성.
