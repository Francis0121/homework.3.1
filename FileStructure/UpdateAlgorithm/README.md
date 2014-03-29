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

<h4>version 0.2 [14.03.30]</h4>
