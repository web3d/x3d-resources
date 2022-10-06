varying vec3 normal;
uniform vec3 decis;

void main()
{
        float intensity;
        vec4 color;
        vec3 n = normalize(normal);

        intensity = dot(vec3(gl_LightSource[0].position),n);

        if (intensity > decis[0])
                color = vec4(0.0,0.5,0.5,1.0);
        else if (intensity > decis[1])
                color = vec4(0.6,0.3,0.3,1.0);
        else if (intensity > decis[2])
                color = vec4(1.0,0.2,0.2,1.0);
        else
                color = vec4(0.0,0.4,0.0,1.0);

        gl_FragColor = color;
} 
