package seamolec.org.app.udjseamolec;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Krisnasw on 9/10/2016.
 */
public interface TugasAPI {

    @GET("/udj2/paket.php")
    Call<JSONResponse> getJSON();
}
