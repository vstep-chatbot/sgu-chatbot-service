import {QueryOptions} from "@tanstack/react-query";
import axios from "axios";

const httpClient = axios.create({baseURL: import.meta.env.VITE_BACKEND_URL});

export default httpClient;
