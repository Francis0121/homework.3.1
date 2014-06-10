//varying vec3 v_color;
uniform sampler2D u_texid;

varying vec2 v_texcoord;

void main(){
	//gl_FragColor = vec4(v_color, 1); // Color는 기본적으로 rgba 로 4차원이다.
	gl_FragColor = texture2D(u_texid, v_texcoord);
}