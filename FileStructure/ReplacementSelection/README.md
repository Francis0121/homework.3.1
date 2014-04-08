<h1>Replacement Selection Algorithm</h1>

<h4>[1] Enviroment & Library</h4> 
Development Tool : Eclipse
Language : Java 8
Library : 
Excel 
- poi-3.9.jar
- poi-ooxml-schemas-3.9.jar
- poi-ooxml-3.9.jar
- dom4j-1.6.1.jar
- xmlbeans-2.3.0.jar
Log 
- log4j-1.2.15.jar
- slf4j-api-1.7.5.jar
- slf4j-log4j12-1.6.6.jar

<h4>[2] Algorithm Summary</h4>

1. Buffer의 입력 화일로 부터 runsize만큼 Record를 읽어서 첫번째 unfrozen List를 생성한다.
2. unforzen List 중 가장 작은(smallest)한 것을 하나의 Run으로 출력한다.
3. Buffer의 입력 화일로 부터 다시 하나의 Record를 읽어오고 읽어온 Record값이 smallest보다 작다면 frozen List에 저장하고 아니라면 unforzen List에 넣는다. 
4. unfrozen List가 0이 될 때까지 2,3을 반복수행한다.
5. unfrozne List가 0이 되면 Run이 하나 생성이 완료된것이고, fronze List를 unfroze List로 넣어 다시 알고리즘을 수행한다.

```
109 49 34 68 45 2 60 38 28 47 16
```

Run 의 생성
```
<Unfroze List>
1. 109 49 34 68 45
2. 109 49 68 45
3. 109 49 68 60
4. 109 68 60
5. 109 68
6. 109
```

```
<Frozen List>
1. 2
2. 2 
3. 2 38
4. 2 38 28
5. 2 38 28 47
6. 2 38 28 47 16
```

```
<Record>
1. 34
2. 34 45
3. 34 45 49
4. 34 45 49 60
5. 34 45 49 60 68
6. 34 45 49 60 68 109
```


