uniform mat4 u_mvp_matrix; // 4차원 Matrix
attribute vec4 a_vertex; 
attribute vec2 a_texcoord;

varying vec2 v_texcoord;
// 속성 Vector 
//attribute vec3 a_color;
//varying vec3 v_color;
 
void main()
{
	// 4 차원 Matrix * Vector 인데 
	// 4 차 * 3 차 임으로 homogenous를 만들 기위해 1.0 을 마지막 원소로 추가시켜서 Vector를 곱한다.
	v_texcoord = a_texcoord;
	gl_Position = u_mvp_matrix * a_vertex;
	//v_color = a_color;
}