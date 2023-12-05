import com.coox.plugins.core.BasicService;
import com.coox.plugins.core.Service;

module core {
    exports com.coox.plugins.core;

    uses Service;
    provides Service with BasicService;
}