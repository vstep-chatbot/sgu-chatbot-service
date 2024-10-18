import {
  useMutation,
  UseMutationOptions,
  useQueryClient,
} from "@tanstack/react-query";
import httpClient from "./httpClient";
import { toast } from "@/hooks/use-toast";
import { AxiosError } from "axios";

type DeleteFileRequest = {
  filename: string;
  type: "contestants" | "scores";
};

async function deleteFile({ filename, type }: DeleteFileRequest) {
  const res = await httpClient.delete("storage/files/" + filename, {
    params: {
      type,
    },
  });

  return res.data;
}

export default function useDeleteFile(
  opts?: UseMutationOptions<unknown, AxiosError<any, any>, DeleteFileRequest>
) {
  const queryClient = useQueryClient();

  return useMutation({
    ...opts,

    mutationFn: deleteFile,

    onMutate(variables) {
      toast({
        title: `Đang xóa file ${variables.filename}...`,
        description: "Vui lòng chờ trong giây lát",
      });

      opts?.onMutate?.(variables);
    },

    onError(error, variables, context) {
      toast({
        title: `Xóa file ${variables.filename} thất bại`,
        variant: "destructive",
        description: error.response?.data?.message,
      });

      opts?.onError?.(error, variables, context);
    },

    onSuccess(data, variables, context) {
      toast({ title: `Xóa file ${variables.filename} thành công` });

      queryClient.invalidateQueries({ queryKey: ["get", variables.type] });

      opts?.onSuccess?.(data, variables, context);
    },
  });
}
