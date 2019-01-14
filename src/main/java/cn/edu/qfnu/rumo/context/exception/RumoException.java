package cn.edu.qfnu.rumo.context.exception;

/**
 * Nemo业务异常类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/08/01
 */
public class RumoException extends RuntimeException {
    private int statusCode;

    public RumoException(int statusCode) {
        this.statusCode = statusCode;
    }

    public RumoException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
