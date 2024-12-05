# 게시판 REST API 명세서
## Auth
### signIn (로그인)
#### URL

POST /api/v1/auth/sign-in

#### request
```
{
    *email : String,
    *password : String
}
```

#### response
- 성공
```
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success."
    token : "jwt...",
    expirationTime : 3600
}

```
- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    code : "VF",
    message : "Validation failed."
}
```
```
- 로그인 실패
Http Status - 401 (Unauthorized)
{
    code : "SF",
    message : "Login informaion mismatch."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### signUp (회원가입)

#### URL
POST /api/v1/auth/sign-up

#### request
```
{
    *email : String,
    *password : String,
    *nickname : String,
    *telNumber : String, // 정수로 받으면 010 입력 시 제일 앞 0이 없어짐
    *address : String,
    addressDetail : String
    *agreedPersonal: true
}
```

#### response

- 성공
```
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    code : "VF",
    message : "Validation failed."
}
```

```
- 중복된 이메일
Http Status - 400 (Bad Request)
{
    code : "DE",
    message : "Duplicate email."
}
```

```
- 중복된 닉네임
Http Status - 400 (Bad Request)
{
    code : "DN",
    message : "Duplicate nickname."
}
```

```
- 중복된 휴대전화번호
Http Status - 400 (Bad Request)
{
    code : "DT",
    message : "Duplicate telephone number."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---
## Board
### latestList (최신 게시물 리스트)

#### URL
GET /api/v1/board/latest-list

#### response

- 성공
```
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success."
    latestList : boardListItem[]
}
```
```
BoardListItem
{
    *boardNumber: int,
    *title: String,
    *content: String,
    boardTitleImage: String,
    *favoriteCount: int,
    *commentCount: int,
    *viewCount: int,
    *writeDateTime: String,
    *writerNickname:String,
    writerProfileImage: String
}
```

- 실패

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### weeklyTop3List (주간 상위 3 게시물 리스트)

#### URL
GET /api/v1/board/top-3

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *top3List : boardListItem[]
}
```

```
BoardListItem
{
    *boardNumber: int,
    *title: String,
    *content: String,
    boardTitleImage: String,
    *favoriteCount: int,
    *commentCount: int,
    *viewCount: int,
    *writeDateTime: String,
    *writerNickname:String,
    writerProfileImage: String

}
```

- 실패
```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### searchList (검색 게시물 리스트)

#### URL
- GET /api/v1/board/search-list/{searchWord}
- GET /api/v1/board/search-list/{searchWord}/{preSearchWord}

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *searchList : boardListItem[]
}
```

```
BoardListItem
{
    *boardNumber: int,
    *title: String,
    *content: String,
    boardTitleImage: String,
    *favoriteCount: int,
    *commentCount: int,
    *viewCount: int,
    *writeDateTime: String,
    *writerNickname:String,
    writerProfileImage: String

}
```

- 실패

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### userBoardList (특정 유저 게시물 리스트)

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *userBoardList : boardListItem[]
}

```
```
BoardListItem
{
    *boardNumber: int,
    *title: String,
    *content: String,
    boardTitleImage: String,
    *favoriteCount: int,
    *commentCount: int,
    *viewCount: int,
    *writeDateTime: String,
    *writerNickname:String,
    writerProfileImage: String

}
```

- 실패

```
- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    *code : "NU",
    *message : "No Existed User"
}

```
```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### boardDetail (게시물 상세)

#### URL
GET /api/v1/board/{boardNumber}

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *boardNumber: int,
    *title: String,
    *content: String,
    *boardImageList: String[],
    *writeDateTime: String,
    *writerEmail: String,
    *writerNickname:String,
    writerProfileImage: String
}
```

- 실패

```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number."
}
```


```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### favoriteList (좋아요 리스트)

#### URL
GET /api/v1/board/{boardNumber}/favorite-list

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *favoriteList : FavoriteListItem[]
}
```

```
FavoriteListItem
{
    *email: String,
    *nickname: String,
    *profileImage: String

}
```

- 실패
```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number."
}
```
```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### commentList (댓글 리스트)

#### URL
GET /api/v1/board/{boardNumber}/comment-list

#### response
- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *commentList: CommentListItem[]
}
```
```
CommentListItem{
    *nickname: String,
    profileImage: String,
    *writeDateTime: String,
    *content: String
}
```
- 실패
```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### boardWrite (게시물 작성)

#### URL
POST /api/v1/board

#### Header
```
Authorization : Bearer Token
```

#### request
```
{
    *title: String,
    *content: String,
    *boardImageList: String[]
}
```

#### response
- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    *code : "VF",
    *message : "Validation failed."
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```

```
- 인증 실패
Http Status - 401 (Unauthorized)
{
    *code : "AF",
    *message : "Authorization Failed."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### postComment (댓글 작성)

#### URL
POST /api/v1/board/{boardNumber}/comment

#### Header
```
Authorization : Bearer Token
```

#### request

```
{
    *content: String
}
```

#### resposne

- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    *code : "VF",
    *message : "Validation failed."
}
```

```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number"
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```
```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### boardUpdate (게시물 수정)

#### URL
PATCH /api/v1/board/{boardNumber}

#### Header
```
Authorization : Bearer Token
```

#### request
```
{
    *title: String,
    *content: String,
    *boardImageList: String[]
}
```

#### response

- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    *code : "VF",
    *message : "Validation failed."
}
```
```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number"
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```
```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```
```
- 권한 없음
Http Status - 403 (Forbidden)
{
    *code : "NP",
    *message : "No Permission"
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### putFavorite (좋아요 기능)

#### URL
PUT /api/v1/board/{boardNumber}/favorite

#### Header
```
Authorization : Bearer Token
```


#### response
- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    *code : "VF",
    *message : "Validation failed."
}
```
```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number"
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```
```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### boardDelete (게시물 삭제)

#### URL
DELETE /api/v1/board/{boardNumber}

#### Header
```
Authorization : Bearer Token
```

#### resposne

- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    *code : "VF",
    *message : "Validation failed."
}
```
```
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    *code : "NB",
    *message : "No Existed Board Number"
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```
```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```
```
- 권한 없음
Http Status - 403 (Forbidden)
{
    *code : "NP",
    *message : "No Permission"
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

## Search
### popularWordList (인기 검색어 리스트)

#### URL
GET /api/v1/search/popular-list

#### response

- 성공
```
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success."
    popularWordList : String[]
}
```

- 실패

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### relativeWordList (관련 검색어 리스트)

#### URL
GET /api/v1/search/{searchWord}/relation-list

#### response

- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *relativeWordList : String[]
}
```

- 실패

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

## User
### getUser (유저 정보)

#### URL
GET /api/v1/user/{email}

#### response
- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *email : String,
    *nickname : String,
    profileImage : String
}

```
- 실패

```
- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    *code : "NU",
    *message : "No Existed User"
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### getLoginUser (로그인 유저 정보)

#### URL
GET /api/v1/user

#### Header
```
Authorization : Bearer Token
```

#### response
- 성공

```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
    *email : String,
    *nickname : String,
    profileImage : String
}

```
- 실패

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```

```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    *code : "DBE",
    *message : "Database Error"
}
```

---

### patchNickname (닉네임 수정)
#### URL
PATCH /api/v1/user/nickname

#### Header
```
Authorization : Bearer Token
```
#### request
```
{
    *nickname: String
}
```

#### response
- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 유효성 검사 실패
Http Status - 400 (Bad Request)
{
    code : "VF",
    message : "Validation failed."
}
```

```
- 중복된 닉네임
Http Status - 400 (Bad Request)
{
    code : "DN",
    message : "Duplicate nickname."
}
```

```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```

```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```

```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```

---

### pathchProfileImage (프로필 이미지 수정)

#### URL
PATCH /api/v1/user/profile-image

#### Header
```
Authorization : Bearer Token
```

#### request
```
{
    profileImage: String
}
```

#### response
- 성공
```
Http Status - 200 (OK)
{
    *code : "SU",
    *message : "Success."
}
```

- 실패
```
- 존재하지 않는 유저
Http Status - 401 (Unauthorized)
{
    *code : "NU",
    *message : "No Existed User"
}
```

```
- 인증 실패
Http Status - 401 (Unauthorized) 

{
    *code: "AF",
    *message: "Authorization Failed."
}
```
```
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code : "DBE",
    message : "Database Error"
}
```