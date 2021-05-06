# Board_Project_Version2(  AWS S3  )

###  AWS S3 이미지 업로드
###  댓글 구현
###  조회수 구현(조회수 기반 인기 게시물 기능으로 업그레이드할 예정)

<br/>


# 1. AWS S3 업로드 구현하기

> < 업로드 된 이미지 url >
![image](https://user-images.githubusercontent.com/66015002/107307832-d25a3f80-6aca-11eb-920c-9854ae1141db.png)


### 1.1
> AWS의 S3 콘솔에서 버킷을 생성합니다.
https://docs.aws.amazon.com/ko_kr/AmazonS3/latest/user-guide/create-bucket.html  (참조)

### 1.2
> s3의 버킷 정책을 수정합니다.
https://aws.amazon.com/ko/premiumsupport/knowledge-center/s3-access-denied-bucket-policy/  (참조)

### 1.3
> build.gradle에 s3 의존성을 부여합니다.
![image](https://user-images.githubusercontent.com/66015002/107308448-02eea900-6acc-11eb-9098-b0202d083889.png)

### 1.4
> application.yml에 aws 설정을 추가합니다.
<pre>
<code>
cloud:
  aws:
    credentials:
      accessKey: --------------------
      secretKey: --------------------
    s3:
      bucket: (내가 설정한 이름)
    region:
      static: (내가 설정한 지역)
    stack:
      auto: false
</code>
</pre>

> stack에 auto를 false로 지정하는 이유는 서버 구성을 자동화하는 CloudFormation이 자동으로 실행됩니다. 따라서 false로 사용하지 않음을 설정해야 합니다.
안 그럼 오류가 발생합니다.

### 1.5
> s3을 동작하게 하는 service를 구현했습니다.
![image](https://user-images.githubusercontent.com/66015002/107309134-37af3000-6acd-11eb-8d80-ccb82c5bbf46.png)
AmazonS3ClientBuilder를 통해 S3 Client를 가져올 때 자격증명을 구현하는 코드입니다. accessKey, secretKey를 이용하여 자격증명이 이루어집니다.
@Value로 yml에 있는 키 값을 주입받았기 때문에 @PostConstruct로 구현하였습니다.

> s3이 파일을 업로드할 수 있게 동작하는 코드입니다.
![image](https://user-images.githubusercontent.com/66015002/107309440-c754de80-6acd-11eb-8039-fb14f5b27628.png)
외부에 공개할 이미지를 설정하기 때문에 CannedAccessControlList.PublicRead로 설정했습니다

### 1.6
> view에서 enctype="multipart/form-data"로 file 업로드를 구현합니다. 엔티티에서 String fileurl로 지정하여 fileurl 값을 string 으로 보내고 읽어오는 방식입니다.

![image](https://user-images.githubusercontent.com/66015002/107309721-4fd37f00-6ace-11eb-9122-52cbc6bf0028.png)

### 1.7
> 첨부 이미지가 업로드 되고, s3 저장소에도 업로드 되는 모습입니다.
![image](https://user-images.githubusercontent.com/66015002/107309887-a345cd00-6ace-11eb-9214-0bd24eca59fa.png)
![image](https://user-images.githubusercontent.com/66015002/107309944-c1133200-6ace-11eb-90bd-3af99fd3e2a6.png)

<br/>

# 2. 댓글 구현

> 댓글 엔티티- 한 게시물에 여러 댓글이 달릴 수 있다(다대일 N:1) , 여러 사용자들이 댓글을 달 수 있다(다대일 N:1)
![image](https://user-images.githubusercontent.com/66015002/107313688-667dd400-6ad6-11eb-9167-8796c9f3a096.png)

> 댓글 Service - 해당 게시글의 id값을 참조받아 게시글, 유저, 댓글 정보를 담아 저장하는 방식
![image](https://user-images.githubusercontent.com/66015002/107313815-a8a71580-6ad6-11eb-875c-a6ce7ff38e74.png)

> 댓글 Controller - post 방식으로 service를 동작시킨다
![image](https://user-images.githubusercontent.com/66015002/107314085-44d11c80-6ad7-11eb-8b3e-c81d292c0189.png)

> view - 게시글 하단에 여러 사용자의 댓글을 볼 수 있다.
![image](https://user-images.githubusercontent.com/66015002/107314264-9b3e5b00-6ad7-11eb-935b-d88cbabe95b1.png)

<br/>

# 3. 조회수 구현

> 게시글 엔티티에 Long 값으로 조회수를 설정하여 해당 게시글에 들어가면 조회수가 1씩 증가하는 방법으로 간단히 구현할 수 있다. 이 조회수는 향후 Version 3에서 조회수를 기반으로하여
인기 게시물을 선정하는 척도로 사용할 예정이다. 아래와 같이 viewCount++를 함으로써 1씩 증가하는 로직이 만들어졌다.

![image](https://user-images.githubusercontent.com/66015002/107314510-19026680-6ad8-11eb-98be-ce1f2d79d3fb.png)

> 그리고 controller에서 게시글을 읽을 때 아까 구현한 로직을 service에 담아 하나씩 증가하도록 설정하였다.
![image](https://user-images.githubusercontent.com/66015002/107314658-7991a380-6ad8-11eb-9f7a-5fbf5a7ffc4a.png)


> 그러면 다음과 같이 view에서 확인할 수 있다.
![image](https://user-images.githubusercontent.com/66015002/107314733-a80f7e80-6ad8-11eb-8517-c7307f6add92.png)
