package com.pingsec.dev.service;

import io.rancher.type.Project;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RancherCustomService {
    @POST("projects/{id}/registrationtoken")
    Call<Project> createRegistrationToken(@Path("id") String id);

}
