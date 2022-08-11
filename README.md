# 스프링 심화 주차 - 과제 프로젝트

## 팀원 소개

이동욱: [@Moveuk](https://github.com/Moveuk)    
김유정: [@mediumorange](https://github.com/mediumorange)    
노정민: [@lumill925](https://github.com/lumill925)    
황순욱: [@SoonWookHwang](https://github.com/SoonWookHwang)    

## 기능소개

### 기능0: **게시글 조회**

- **게시글 1개 조회(상세 페이지용) 시, 댓글/대댓글/좋아요 수 모두 포함하여 response**

### 기능1: **게시글 좋아요 기능 및 댓글 좋아요**

- **게시글 좋아요 기능 및 댓글 좋아요**
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 좋아요 가능하게 하기
    - **`Exception`** AccessToken이 없거나, 유효하지 않은 Token일 때 ‘로그인이 필요합니다.’를 200 정상 응답으로 나타내기
    - **API 종류**
        1.  좋아요 등록 API
            - AccessToken이 있고, 유효한 Token일 때만 요청 가능하도록 하기
            - 개수 +1
        2. 좋아요 취소 API
            - AccessToken이 있고, 유효한 Token일 때만 요청 가능하도록 하기
            - 개수 +1

### 기능2: **대댓글**

- **대댓글**
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 댓글/대댓글 작성 가능하게 하기
    - 댓글 리스트 response할 때 대댓글 리스트도 모두 포함해서 보여주기
    - **`Exception`** AccessToken이 없거나, 유효하지 않은 Token일 때 ‘로그인이 필요합니다.’를 200 정상 응답으로 나타내기
    - **API 종류**
        1.  대댓글 목록 조회 API
            - AccessToken이 없어도 댓글 목록 조회가 가능하도록 하기
            - 조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
        2. 대댓글 작성 API
            - AccessToken이 있고, 유효한 Token일 때만 댓글 작성이 가능하도록 하기
        3. 대댓글 수정 API
            - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 댓글만 수정 가능하도록 하기
        4. 대댓글 삭제 API
            - AccessToken이 있고, 유효한 Token이면서 해당  사용자가 작성한 댓글만 삭제 가능하도록 하기

### 기능3: **이미지 업로드**

- (게시글에 들어갈) **이미지 업로드**
    - hint. AWS IAM, SDK, S3
    - 게시글당 1개의 이미지만 업로드 가능하다는 전제로 진행
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 댓글/대댓글 작성 가능하게 하기
    - **`Exception`** 해당 게시글의 id를 반드시 받도록 하고, 존재하지 않는 게시글 id 일 때, ‘존재하지 않는 게시글 입니다.’를 200 정상 응답으로 나타내기
    - **API 종류**
        1.  이미지 조회 API
            - AccessToken이 없어도 게시글과 함께 이미지 조회가 가능하도록 하기
            - 조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
        2. 이미지 등록 API
            - AccessToken이 있고, 유효한 Token일 때만 이미지 업로드 API 요청이 가능하도록 하기

### 기능4: 게시글 기본 API

- **게시글**
    - 이미지 업로드 후, 받아온 response의 imgUrl을 포함하여 글 제목,내용,이미지 주소를 함께 업로드한다.
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 요청 가능하게 하기
    - **`Exception`** 해당 게시글 조회 시, 존재하지 않는 게시글 id 일 때, ‘존재하지 않는 게시글 입니다.’를 200 정상 응답으로 나타내기
    - API 종류
        1. 전체 게시글 목록 조회 API
        2. 게시글 하나 조회 API
        3. 게시글 작성 API
        4. 게시글 수정 API
        5. 게시글 삭제 API

### 기능5: **마이페이지 & 게시글과 댓글 좋아요 개수 표시**

- **마이페이지**
    - AccessToken 속 사용자가 작성한 게시글, 댓글/대댓글, 좋아요한 게시글/댓글 분류하여 response
- **게시글과 댓글 좋아요 개수 표시**
    - AccessToken이 없어도 좋아요 개수 조회가 가능하도록 하기
    - 조회하는 게시글 리스트에 작성된 모든 좋아요 개수를 게시글 리스트 response에 포함하기
    - 조회하는 각 게시글에 작성된 모든 좋아요 개수를 게시글 리스트 response에 포함하기
    - 조회하는 각 댓글/대댓글에 작성된 모든 좋아요 개수를 댓글 리스트 response에 포함하기

### 기능6: **스케줄러 →  기능 변경**

- **스케줄러1**
    - 매일 AM 02:00 마다 사용되고 있지 않은 이미지를 S3에서 삭제되도록 스케줄러 만들기
    - 삭제될 때마다 `"게시물 <{게시물 이름}>이 삭제되었습니다."` 라는 info level log 남기기
    
- **스케줄러2**
    - 매일 AM 01:00 마다 댓글이 0개인 게시물 전체 삭제하기
    - 삭제될 때마다 `"게시물 <{게시물 이름}>이 삭제되었습니다."` 라는 info level log 남기기
![image](https://user-images.githubusercontent.com/84966961/184093980-bbcebf0c-3c29-4e56-8997-9ed068845253.png)


## ERD

![image](https://user-images.githubusercontent.com/84966961/184094126-9a544538-77a0-433a-9bf7-ddb0cfd2ad87.png)

## API 예시

![image](https://user-images.githubusercontent.com/84966961/184094255-ecbee941-3d94-438d-bfae-cf00e8e700ef.png)

