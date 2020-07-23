# annotation 정리
## JAVA

### @NotNull

- 필드가 null 값인지 체크

### @Pattern()

- 패턴으로 정해진 문자만 입력가능

<br/>

## JPA

### @Entity

- 데이터베이스의 테이블에 매핑할 클래스에 붙임

### @Id

- 기본키 매핑

### @Column("")

- 테이블의 컬렁명 매핑. 기본값은 객체 필드명과 동일하게 지정.

<br/>

## Lombok

### @AllArgsConstructor

- 모든 필드 값을 파라미터로 받는 생성자 생성

### @NoArgsConstructor

- 파라미터가 없는 기본 생성자 생성

### @Getter, @Setter

- getter, setter method 일일이 선언하지 않아도 자동생성

### @ToString

- ToString method 선언하지 않아도 자동생성

### @Data

- @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode 를 한꺼번에 설정

### @Builder

- Builder 클래스 생성

