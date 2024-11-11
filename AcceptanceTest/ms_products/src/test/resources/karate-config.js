function fn() {
    const config = {
        headers: {},
        urlBase: 'http://localhost:8081/api/v1/',
    };

    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    karate.configure('ssl', true);
    return config;
}