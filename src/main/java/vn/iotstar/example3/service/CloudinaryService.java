package vn.iotstar.example3.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    public String upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        if (cloudName == null || cloudName.isBlank())
            throw new IllegalStateException("Chưa cấu hình CLOUDINARY_CLOUD_NAME/API_KEY/API_SECRET");
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "example3"));
        return result.get("secure_url").toString();
    }
}
