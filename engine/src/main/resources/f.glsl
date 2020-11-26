#version 450 core

#define MAX_LIGHTS 32

out vec4 color;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
};


struct DirectionalLight {
    vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight {
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

struct SpotLight {
    vec3 position;
    vec3 direction;
    float cutOff;
    float outerCutOff;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

in vec3 normal;
in vec3 fPos;

uniform vec3 viewPos;

uniform Material material;
uniform DirectionalLight light;

uniform int numDirectionalLights;
uniform int numPointLights;
uniform int numSpotLights;


vec3 directionalLight(DirectionalLight directionalLight) {
    // ambient
    vec3 ambient  = directionalLight.ambient * material.ambient;

    // diffuse
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(-directionalLight.direction);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse  = directionalLight.diffuse * (diff * material.diffuse);

    // specular
    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = directionalLight.specular * (spec * material.specular);

    vec3 result = ambient + diffuse + specular;
    return result;
}

vec3 pointLight(PointLight pointLight) {
    // ambient
    vec3 ambient  = pointLight.ambient * material.ambient;

    // diffuse
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(pointLight.position - fPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse  = pointLight.diffuse * (diff * material.diffuse);

    // specular
    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = pointLight.specular * (spec * material.specular);

    // attenuation
    float distance    = length(pointLight.position - fPos);
    float attenuation = 1.0 / (pointLight.constant + pointLight.linear * distance +
    pointLight.quadratic * (distance * distance));

    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;

    vec3 result = ambient + diffuse + specular;
    return result;
}

vec3 spotLight(SpotLight spotLight) {
    vec3 lightDir = normalize(spotLight.position - fPos);

    // ambient
    vec3 ambient = spotLight.ambient * material.diffuse;

    // diffuse
    vec3 norm = normalize(normal);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = spotLight.diffuse * diff * material.diffuse;

    // specular
    vec3 viewDir = normalize(viewPos - fPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = spotLight.specular * spec * material.specular;

    // spotlight (soft edges)
    float theta = dot(lightDir, normalize(-spotLight.direction));
    float epsilon = (spotLight.cutOff - spotLight.outerCutOff);
    float intensity = clamp((theta - spotLight.outerCutOff) / epsilon, 0.0, 1.0);
    diffuse  *= intensity;
    specular *= intensity;

    // attenuation
    float distance    = length(spotLight.position - fPos);
    float attenuation = 1.0 / (spotLight.constant + spotLight.linear * distance + spotLight.quadratic * (distance * distance));

    // ambient  *= attenuation; // remove attenuation from ambient, as otherwise at large distances the light would be darker inside than outside the spotlight due the ambient term in the else branche
    diffuse   *= attenuation;
    specular *= attenuation;
    diffuse  *= intensity;
    specular *= intensity;

    vec3 result = ambient + diffuse + specular;
    return result;
}


void main() {
    color = vec4(directionalLight(light), 1.0);
}