import {useQuery, UseQueryOptions} from "@tanstack/react-query";
import httpClient from "./httpClient";
import {MyRecord} from "@/types/types";

type GetRecordsRequest = {
    recordType: "contestants" | "scores";
    page?: number;
    limit?: number;
};

async function getDocuments(request: GetRecordsRequest) {
    const res = await httpClient.get<MyRecord[]>(request.recordType);
    return res.data;
}

export default function useGetDocuments(
    request: GetRecordsRequest,
    opts?: Omit<UseQueryOptions<MyRecord[]>, "queryKey" | "queryFn">
) {
    return useQuery({
        ...opts,
        queryKey: ["get", JSON.stringify(request)],
        queryFn: () => getDocuments(request),
    });
}
