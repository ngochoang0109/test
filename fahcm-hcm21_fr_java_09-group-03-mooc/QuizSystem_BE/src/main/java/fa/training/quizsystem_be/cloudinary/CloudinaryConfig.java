package fa.training.quizsystem_be.cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CloudinaryConstants.CLOUD_NAME,
                "api_key", CloudinaryConstants.API_KEY,
                "api_secret", CloudinaryConstants.API_SECRET,
                "secure", true));
    }
}

