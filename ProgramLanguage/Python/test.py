# coding: cp949
pocket = ['paper', 'money', 'handphone']
watch = 1
if 'money' in pocket:
    print("택시를 타고 간다")
elif 'book' not in pocket:
    pass
elif watch:
    print("택시를 타고 간다")
else:
    print("걸어 간다")

coffee = 10
money = 300
while money:
    print("돈을 받았으니 커피를 줍니다.")
    coffee = coffee - 1
    print("남은 커피의 양은 %d 입니다." % coffee)
    if not coffee:
        print("커피가 다 떨어졌습니다. 판매를 중지합니다.")
        break
