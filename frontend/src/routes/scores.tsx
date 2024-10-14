import {createFileRoute} from "@tanstack/react-router";
import DataPage from "@/components/layouts/DataPage";

export const Route = createFileRoute("/scores")({
    component: () => <DataPage recordType="scores" title="Danh sách File điểm"/>,
});
