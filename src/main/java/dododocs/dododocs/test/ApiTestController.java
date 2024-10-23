package dododocs.dododocs.test;

import dododocs.dododocs.test.dto.FindDbTestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiTestController {

    @GetMapping("/server")
    public ResponseEntity<Void> findTrueResponse() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/db")
    public ResponseEntity<FindDbTestResponse> findDBResponse() {
        return ResponseEntity.ok(new FindDbTestResponse(1L, "Hello DKOS!"));
    }
}
