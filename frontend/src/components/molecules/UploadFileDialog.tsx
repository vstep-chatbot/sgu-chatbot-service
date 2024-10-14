import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "../ui/input";
import httpClient from "@/services/httpClient";
import { toast } from "@/hooks/use-toast";
import { useQueryClient } from "@tanstack/react-query";
import { FormEvent } from "react";

type UploadFileDialogProps = {
  setOpen?: (open: boolean) => void;
  type: "contestants" | "scores";
};

export default function UploadFileDialog({
  setOpen,
  type,
}: UploadFileDialogProps) {
  const queryClient = useQueryClient();

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    const form = e.currentTarget as HTMLFormElement;
    const formData = new FormData(form);

    httpClient
      .post(import.meta.env.VITE_BACKEND_SERVER + "/storage", formData)
      .then(() => {
        setOpen && setOpen(false);
        toast({ title: "Thêm file thành công" });
        queryClient.invalidateQueries({ queryKey: ["get", type] });
      })
      .catch(function (error) {
        if (error.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.error(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
          toast({
            title: "Thêm file thất bại",
            variant: "destructive",
            description: error.response.data?.message,
          });
        }
        setOpen && setOpen(false);
      });
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="default">Thêm file</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Thêm file</DialogTitle>
          <DialogDescription>
            Chọn file Excel chứa thông tin phòng thi để thêm vào hệ thống
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} id="upload-form">
          <Input
            required
            type="file"
            name="file"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          />
          <input
            className="hidden"
            type="text"
            name="record_type"
            value={type}
          />
        </form>
        <DialogFooter>
          <Button type="submit" form="upload-form">
            Thêm
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
