; 중첩 리스트를 중첩이 없이 element들만 나열한 1차원 형태로...
; (flatten '(a (b) (c (d e)) ((f)))) => '(a b c d e f)
(defun flatten (x)
   (cond ( (null x) x )
	 ( (atom (car x)) (cons (car x) (flatten (cdr x))) )
	 ( t (append (flatten (car x)) (flatten (cdr x))) )
   )
)

; atom이 list의 member인지 참(t)/거짓(nil)으로 return
; (memberp 'a '(b a n)) => t
(defun memberp (atm lst)
   (cond  ( (null lst) nil )
          ( (eq atm (car lst)) t )
   	  ( t (memberp atm (cdr lst)) )
   )
)

; 리스트의 원소 개수 count -- 중첩된 것도 각각 count함!
; (count-all '(a (b c) d)) => 4
(defun count-all (x)
   (cond ( (null x) 0 )
         ( (atom (car x)) (1+ (count-all (cdr x))) )
         ( t (+ (count-all (car x)) (count-all (cdr x))) )))

; lst에서 첫번째 발견되는 atm을 삭제함
; (remove-1st 'a '(b a n a n a)) => '(b n a n a)
(defun remove-1st (atm lst)
   (cond ( (not (equal atm (car lst))) (cons (car lst) (remove-1st atm (cdr lst))) )
         ( t (cdr lst) )
   )
)

; lst에서 마지막 발견되는 atm을 삭제함
; (remove-last 'a '(b a n a n a)) => '(b a n a n)
(defun remove-last (atm lst)
   (reverse (remove-1st atm (reverse lst))))

; list의 원소들을 역순으로....
; (list-reverse '(a b c d)) => '(c d b a)
(defun list-reverse (list)
   (do ( (x list (cdr x))
         (y '() (cons (car x) y)) )
       ( (endp x) y )
   )
)

; 리스트 z의 원소 중에서 모든 y를 x로 대치함
; (subst2 'c 'a '(b a n a n a)) => '(b c n c n c)
(defun subst2 (x y z)
   (cond ( (equal y z) x )
         ( (atom z) z )
         ( t (cons (subst2 x y (car z)) (subst2 x y (cdr z))) )
   )
)

; 중첩된 리스트의 깊이(depth) 계산
; (depth '((a))) => 2, (depth '(a (b (c) d) e)) => 3
(defun depth (x)
   (cond ( (atom x) 0 )
         ( t (max (1+ (depth (car x))) (depth (cdr x))) )
   )
)

;========================================================================
; 절차적(procedual) 프로그램 사용 예제 -- <주의> 함수형 언어 특성 아님!!!
; 함수형 프로그램은 이런 방법으로 작성하면 안됨!!!
;========================================================================
(defun sum-of ()
   (prog (n)
	 (princ "Input integer: ")
	 (setq n (read))
  	 (do ( (i 1 (+ i 1))
  	       (sum 0 (+ sum i)) )
   	     ( (> i n) sum )
   	     (princ "1 + ... + ")
   	     (prin1 (1- i))
   	     (princ " = ")
   	     (prin1 sum)
   	     (terpri)
   	 )
   	 (return n)
   )
)

; 위 절차적 프로그램은 아래와 같이 recursion을 이용하여 작성해야 함!!!
(defun sumof (n)
   (cond ( (<= n 1) 1 )
         ( t (+ n (sumof (1- n))) )))

)