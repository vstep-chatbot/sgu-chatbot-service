import axios from "axios";

const httpClient = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_SERVER,
});

export default httpClient;
