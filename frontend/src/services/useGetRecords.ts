import { useQuery, UseQueryOptions } from "@tanstack/react-query";
import httpClient from "./httpClient";
import { GetContestantsDto, GetScoresDto, SERVICE_TYPE } from "@/types/types";

type GetRecordsRequest = {
  recordType: SERVICE_TYPE;
  page?: number;
  limit?: number;
};

async function getDocuments(request: GetRecordsRequest) {
  const res = await httpClient.get(request.recordType);
  return res.data;
}

export default function useGetRecords<T extends SERVICE_TYPE>(
  request: GetRecordsRequest,
  opts?: Omit<
    UseQueryOptions<
      (T extends "contestants"
        ? GetContestantsDto
        : T extends "scores"
          ? GetScoresDto
          : never)[]
    >,
    "queryKey" | "queryFn"
  >
) {
  return useQuery({
    staleTime: 1000 * 60 * 15,
    ...opts,
    queryKey: ["get", request.recordType, request.page, request.limit],
    queryFn: () => getDocuments(request),
  });
}
