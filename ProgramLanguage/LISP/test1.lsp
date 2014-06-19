; 두 개의 정수에 대한 평균값 계산
; (average 10 20) => 15
(defun average (x y) (/ (+ x y) 2))

; n factorial 계산
; (fact 3) => 6
(defun fact (n)
	(cond ( (<= n 1) 1 )
	      ( t (* n (fact (1- n))))))

; 두 개의 리스트를 연결하여 하나로 만듦 - 2번째 리스트를 1번째 리스트 뒤에 추가
; (append2 '(a b) '(c d)) => '(a b c d)
(defun append2 (x y)
	(cond ( (null (cdr x)) (cons (car x) y) )
	      ( t  (cons (car x) (append (cdr x) y)) )
	)
)

; 리스트 내용을 역순으로...
; (reverse '(a (b c) e)) => '(e (b c) a)
(defun reverse2 (x)
	(cond ( (atom x) x )
	      ( (null (cdr x)) x )
	      ( t (append (reverse2 (cdr x)) (list (car x))) )
	)
)

; 리스트 내용을 역순으로... 원소가 리스트이면 이것도 역순으로...
; (reverse-all '(a (b c d) e)) => '(e (d c b) a)
(defun reverse-all (x)
	(cond ( (atom x) x )
	      ( (null (cdr x)) x )
	      ( t (append (reverse-all (cdr x)) (list (reverse-all (car x)))) )
	)
)
