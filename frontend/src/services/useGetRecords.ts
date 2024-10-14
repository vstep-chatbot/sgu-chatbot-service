import {useQuery, UseQueryOptions} from "@tanstack/react-query";
import httpClient from "./httpClient";
import {GetRecordsDto} from "@/types/types";

type GetRecordsRequest = {
    recordType: "contestants" | "scores";
    page?: number;
    limit?: number;
};

async function getDocuments(request: GetRecordsRequest) {
    const res = await httpClient.get<GetRecordsDto[]>(request.recordType);
    return res.data;
}

export default function useGetRecords(
    request: GetRecordsRequest,
    opts?: Omit<UseQueryOptions<GetRecordsDto[]>, "queryKey" | "queryFn">,
) {
    return useQuery({
        staleTime: 1000 * 60 * 15,
        ...opts,
        queryKey: ["get", request.recordType, request.page, request.limit],
        queryFn: () => getDocuments(request),
    });
}
