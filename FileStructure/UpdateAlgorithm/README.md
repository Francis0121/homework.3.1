<h1>순차 갱신 알고리즘 구현</h1>

```sql
Master File - Key Sorted
  KEY(Integer, NOT NULL, Primary)
  AGE(Integer, NULL), 
  FIRST_NAME(String, NULL), 
  FAMILY_NAME(String, NULL)
```

```sql
[Extend Master File]
Transaction File - Not sorted 
  UPDATE_CODE(String, NOT NULL)
```

<h4>version 0.1 [14.03.29] 알고리즘 구현 완료</h4>

1. Java 1.8 사용
2. File은 Excel을 이용하여 구현 ( org.apache.poi library 를 이용)
3. Error를 보여주기 위해서 Log library 이용 ( org.slf4j.slf4j-log4j12, log4j library)
4. Main 에서 Reader.class와 Writer.class를 실행 시키는 역할
5. Reader의 경우 Excel 데이터를 한 Column씩 읽어 데이터를 List 타입으로 저장
6. Writer의 경우 실질적인 알고리즘을 구현

```java
nextTransaction
nextMaster

while( nextTransaction < transaction.size && nextMaster < master.size){
  if(masterKey < transactionKey){
    writeExcel
    nextMaster
  }else{
    if(masterKey == transactionKey){
      switch(updateCode){
        I : 
          ERROR : DUPLICATE // 중복
          nextTransaction
        U :
          Update row to compare master and transaction
          writeExcel
        D :
          nextTransaction
          nextMaster
        default :
          ERROR : INVALID
          nextTransaction
      }
    }else{
      switch(updateCode){
        I :
          writeExcel(New)
          nextTransaction
        U :
          ERROR : No match Master key
          nextTransaction
        D :
          ERROR : No match Master key
          nextTransaction
        default :
          ERROR : INVALID
          nextTransaction
      }
    }
  }
}

for( i = tPos ; i < transaction.size ; i++ ) // transaction.position
  switch(updateCode){
    I :
      writeExcel(New)
      nextTransaction
    U :
      ERROR : No match Master key
      nextTransaction
    D :
      ERROR : No match Master key
      nextTransaction
    default :
      ERROR : INVALID
      nextTransaction
  }
  
for( i = mPos ; i < master.size ; i++ ) // master.position
  writeExcel
```

<h4>version 0.2 [14.04.01]</h4>

1. Master File 구성 변경 UpdateAlogrithm.xlsx OldMaster Sheet
  - 2의배수로 정렬 되있다고 가정 ( 2 4 6 ... 20 )
2. Transaction File 생성
  - 1~20사이의 정수를 만드는 난수 발생기 사용 60개 레코드
  - 생성되는 시각을 Time Stamp로 작성
  - I, U, D (Update Code) 도 각 레코드에 랜덤하게 적용
3. Transaction File 정렬
  - Key 값을 이용하여 1차 정렬
  - Time Stamp를 이용하여 2차 정렬
4. 순차 갱신 알고리즘 변경
  - 같은 값의 key를 가지고 있는 경우 다음 key값을 확인하는 알고리즘 추가 
  

case 1. transaction > master Insert
```java
if(!uUser.getKey().equals(tUser.getKey())){
  writeRow(sheet, uUser, row++);
}else{
	mUser = uUser;
	mPoint--;
}
```

case 2. transaction == master Update
```java
tPoint += 1;
if (tPoint < transactionUser.size()) {
	tUser = transactionUser.get(tPoint);
}else if(tPoint == transactionUser.size()){
	tUser = new User();
}

if(!mUser.getKey().equals(tUser.getKey())){
	if (++mPoint < masterUser.size()) {
		mUser = masterUser.get(mPoint);
	}
	writeRow(sheet, uUser, row++);
}else{
	mUser = uUser;
}
```

case 3. algorithm end
```java
if(mPoint++ < masterUser.size()){
	writeRow(sheet, mUser, row++);
}
```
