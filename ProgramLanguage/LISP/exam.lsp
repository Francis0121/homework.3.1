(defun C(lis)
	( cond 
		( (null lis) 0 )
		( t
			( cond 
				( 
					(null (car lis)) 
						(C(cdr lis)) 
				)
				( t
					(1+ 
						(C
							(cdr lis) 
						)
					)
				)
			)
		)
	)
)

(defun D(lis)
	(cond 
		( (null lis) 0 )
		( (not (listp (car lis))) 
			(cond 
				( 
					(eq (car lis) nil) 
						(D (cdr lis)) 
				)
				( 
					t 
					(1+ (D (cdr lis)))
				)
			)
		)
		( 
			t 
				(+ 
					(D (car lis)) 
					(D (cdr lis))
				) 
		)
	)
)

(defun ecount (atm lst)
	(cond 
		(  ( null lst )  0 )
		(  ( eq atm (car lst) ) (1+ (ecount atm (cdr lst)) ) )
		( t (ecount atm (cdr lst)) ) 
	)
)

(defun eacount (atm lst)
	(cond 
		( ( null lst ) 0 )
		( ( eq atm (car lst) ) ( 1+ (eacount atm (cdr lst)) ) )
		( ( listp (car lst) ) ( + (eacount atm (car lst)) (eacount atm (cdr lst)) ) )
		( t ( eacount atm (cdr lst) ) )
	)
)

(defun addto(lst k)
	(cond
		( (null lst) nil )
		( (atom lst) (+ lst k) )
		( t
			(cons
				(+ k (car lst))
				(addto (cdr lst) k)
			)
		)
	)
)

(defun interval(m n)
	(cond
		( (>= m n) nil )
		( t 
			(cons m (interval (1+ m) n) )
		)
	)
)

(defun samValues(lst1 lst2)
	(cond
		( (not (eq (list-length lst1) (list-length lst2))) nil )
		( (null lst1) t)
		( 
			(eq (car lst1) (car lst2)) 
				(samValues (cdr lst1) (cdr lst2))
		)
	)
)

(defun sumInteger(n)
	(cond
		( (eq n 0) 0 )
		( t
			(+ (sumInteger (1- n)) n)
		)
	)
)

(defun numList(n)
	(cond
		( (eq n 0) nil)
		( t 
			(cons n (numList(1- n)) )
		)
	)
)

(defun squareAll(lst)
	(cond
		( (null lst) nil )
		( (atom lst) (* lst lst) )
		( t
			(cons 
				(* (car lst) (car lst) )
				( squareAll (cdr lst) )
			)
		)
	)
)