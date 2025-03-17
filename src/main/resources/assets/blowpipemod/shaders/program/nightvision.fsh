#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;
uniform float Brightness;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);

    float brightness = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 nightVision = vec3(0.1, 0.8, 0.1) * brightness;
    nightVision *= Brightness;

    fragColor = vec4(nightVision, color.a);
}
