import {Button} from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import {Input} from "../ui/input";

type UploadFileDialogProps = {
    setOpen?: (open: boolean) => void;
};

export default function UploadFileDialog({setOpen}: UploadFileDialogProps) {
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
                <form
                    target="_blank"
                    method="POST"
                    encType="multipart/form-data"
                    id="upload-form"
                    action={import.meta.env.VITE_BACKEND_SERVER + "/storage/"}
                >
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
                        value="contestants"
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
